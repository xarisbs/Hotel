import { Component, inject, signal, OnInit, computed } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReservaService } from '../../services/reserva.service';
import { ClienteService } from '../../services/cliente.service';
import { HabitacionService } from '../../services/habitacion.service';
import { Reserva, EstadoReserva } from '../../models/reserva.model';
import { Cliente } from '../../models/cliente.model';
import { Habitacion } from '../../models/habitacion.model';
import { formatPen, formatDate, estadoBadgeClass, estadoLabel } from '../../shared/utils/format.utils';

@Component({
  selector: 'app-reservas',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './reservas.component.html',
  styleUrl: './reservas.component.scss',
})
export class ReservasComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly reservaService = inject(ReservaService);
  private readonly clienteService = inject(ClienteService);
  private readonly habitacionService = inject(HabitacionService);

  readonly reservas = signal<Reserva[]>([]);
  readonly clientes = signal<Cliente[]>([]);
  readonly habitaciones = signal<Habitacion[]>([]);
  readonly message = signal<string | null>(null);
  readonly error = signal<string | null>(null);
  readonly showForm = signal(false);
  readonly selectedMonth = signal(new Date());

  readonly formatPen = formatPen;
  readonly formatDate = formatDate;
  readonly estadoBadgeClass = estadoBadgeClass;
  readonly estadoLabel = estadoLabel;

  readonly form = this.fb.nonNullable.group({
    idHuesped: [0, Validators.min(1)],
    idHabitacion: [0, Validators.min(1)],
    fechaCheckIn: ['', Validators.required],
    fechaCheckOut: ['', Validators.required],
    cantidadHuespedes: [1, [Validators.required, Validators.min(1)]],
    observaciones: [''],
  });

  readonly calendarDays = computed(() => this.buildCalendar(this.selectedMonth()));
  readonly monthLabel = computed(() => {
    const d = this.selectedMonth();
    return d.toLocaleDateString('es-PE', { month: 'long', year: 'numeric' });
  });

  readonly reservasActivas = computed(() =>
    this.reservas().filter((r) => r.estado === 'CHECK_IN' || r.estado === 'CONFIRMADA')
  );

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(): void {
    this.reservaService.list().subscribe({
      next: (r) => this.reservas.set(r),
      error: () => this.error.set('No se pudieron cargar las reservas'),
    });
    this.clienteService.list().subscribe({
      next: (c) => {
        this.clientes.set(c);
        if (c.length && this.form.controls.idHuesped.value === 0) {
          this.form.patchValue({ idHuesped: c[0].id ?? 0 });
        }
      },
    });
    this.habitacionService.list().subscribe({
      next: (h) => {
        this.habitaciones.set(h.filter((x) => x.estado === 'DISPONIBLE' || x.estado === 'OCUPADA'));
        if (h.length && this.form.controls.idHabitacion.value === 0) {
          const disp = h.find((x) => x.estado === 'DISPONIBLE') ?? h[0];
          this.form.patchValue({ idHabitacion: disp.id ?? 0 });
        }
      },
    });
  }

  prevMonth(): void {
    const d = new Date(this.selectedMonth());
    d.setMonth(d.getMonth() - 1);
    this.selectedMonth.set(d);
  }

  nextMonth(): void {
    const d = new Date(this.selectedMonth());
    d.setMonth(d.getMonth() + 1);
    this.selectedMonth.set(d);
  }

  buildCalendar(ref: Date): { date: Date; inMonth: boolean; reservas: Reserva[] }[] {
    const year = ref.getFullYear();
    const month = ref.getMonth();
    const first = new Date(year, month, 1);
    const startDay = first.getDay() === 0 ? 6 : first.getDay() - 1;
    const days: { date: Date; inMonth: boolean; reservas: Reserva[] }[] = [];

    for (let i = startDay - 1; i >= 0; i--) {
      const d = new Date(year, month, -i);
      days.push({ date: d, inMonth: false, reservas: this.reservasOnDate(d) });
    }

    const lastDate = new Date(year, month + 1, 0).getDate();
    for (let d = 1; d <= lastDate; d++) {
      const date = new Date(year, month, d);
      days.push({ date, inMonth: true, reservas: this.reservasOnDate(date) });
    }

    while (days.length % 7 !== 0) {
      const d = new Date(year, month + 1, days.length - startDay - lastDate + 1);
      days.push({ date: d, inMonth: false, reservas: this.reservasOnDate(d) });
    }

    return days;
  }

  reservasOnDate(date: Date): Reserva[] {
    const iso = this.toIso(date);
    return this.reservas().filter((r) => {
      if (!r.fechaCheckIn || !r.fechaCheckOut) {
        return false;
      }
      return iso >= r.fechaCheckIn && iso < r.fechaCheckOut;
    });
  }

  toIso(d: Date): string {
    const y = d.getFullYear();
    const m = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    return `${y}-${m}-${day}`;
  }

  isToday(d: Date): boolean {
    const t = new Date();
    return d.toDateString() === t.toDateString();
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    const raw = this.form.getRawValue();
    this.reservaService
      .create({
        idHuesped: Number(raw.idHuesped),
        idHabitacion: Number(raw.idHabitacion),
        fechaCheckIn: raw.fechaCheckIn,
        fechaCheckOut: raw.fechaCheckOut,
        cantidadHuespedes: Number(raw.cantidadHuespedes),
        observaciones: raw.observaciones || undefined,
      })
      .subscribe({
        next: () => {
          this.message.set('Reserva creada exitosamente');
          this.showForm.set(false);
          this.loadAll();
        },
        error: (err) => {
          const msg = err?.error?.message ?? 'No se pudo crear la reserva';
          this.error.set(typeof msg === 'string' ? msg : 'Error al crear reserva');
        },
      });
  }

  cambiarEstado(id: number | undefined, estado: EstadoReserva): void {
    if (!id) {
      return;
    }
    this.reservaService.actualizarEstado(id, { estado }).subscribe({
      next: () => {
        this.message.set(`Estado actualizado: ${estadoLabel(estado)}`);
        this.loadAll();
      },
      error: () => this.error.set('No se pudo actualizar el estado'),
    });
  }

  canCheckIn(r: Reserva): boolean {
    return r.estado === 'PENDIENTE' || r.estado === 'CONFIRMADA';
  }

  canCheckOut(r: Reserva): boolean {
    return r.estado === 'CHECK_IN';
  }
}
