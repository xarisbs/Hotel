import { Component, inject, signal, OnInit, computed } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { HabitacionService } from '../../services/habitacion.service';
import { TipoHabitacionService } from '../../services/tipo-habitacion.service';
import { Habitacion, EstadoHabitacion } from '../../models/habitacion.model';
import { TipoHabitacion } from '../../models/tipo-habitacion.model';
import { TokenStorage } from '../../core/auth/token.storage';
import {
  formatPen,
  estadoBadgeClass,
  estadoLabel,
  roomImageGradient,
} from '../../shared/utils/format.utils';

@Component({
  selector: 'app-habitaciones',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './habitaciones.component.html',
  styleUrl: './habitaciones.component.scss',
})
export class HabitacionesComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly habitacionService = inject(HabitacionService);
  private readonly tipoService = inject(TipoHabitacionService);
  private readonly tokenStorage = inject(TokenStorage);

  readonly habitaciones = signal<Habitacion[]>([]);
  readonly tipos = signal<TipoHabitacion[]>([]);
  readonly message = signal<string | null>(null);
  readonly showForm = signal(false);
  readonly filterEstado = signal<string>('TODOS');

  readonly formatPen = formatPen;
  readonly estadoBadgeClass = estadoBadgeClass;
  readonly estadoLabel = estadoLabel;
  readonly roomImageGradient = roomImageGradient;

  readonly isAdmin = computed(() => this.tokenStorage.hasRole('ROLE_ADMIN'));

  readonly filtered = computed(() => {
    const f = this.filterEstado();
    const list = this.habitaciones();
    if (f === 'TODOS') {
      return list;
    }
    return list.filter((h) => h.estado === f);
  });

  readonly stats = computed(() => {
    const list = this.habitaciones();
    return {
      total: list.length,
      disponibles: list.filter((h) => h.estado === 'DISPONIBLE').length,
      ocupadas: list.filter((h) => h.estado === 'OCUPADA').length,
    };
  });

  readonly form = this.fb.nonNullable.group({
    numero: ['', Validators.required],
    piso: [1, [Validators.required, Validators.min(1)]],
    descripcion: [''],
    idTipoHabitacion: [0, Validators.min(1)],
    precioPorNoche: [150, Validators.min(0.01)],
  });

  readonly estados: { value: string; label: string }[] = [
    { value: 'TODOS', label: 'Todas' },
    { value: 'DISPONIBLE', label: 'Disponibles' },
    { value: 'OCUPADA', label: 'Ocupadas' },
    { value: 'LIMPIEZA', label: 'Limpieza' },
    { value: 'MANTENIMIENTO', label: 'Mantenimiento' },
  ];

  ngOnInit(): void {
    this.tipoService.list().subscribe({
      next: (t) => {
        this.tipos.set(t);
        if (t.length && this.form.controls.idTipoHabitacion.value === 0) {
          this.form.patchValue({ idTipoHabitacion: t[0].id ?? 0 });
        }
      },
    });
    this.load();
  }

  load(): void {
    this.habitacionService.list().subscribe({
      next: (data) => this.habitaciones.set(data),
    });
  }

  toggleForm(): void {
    this.showForm.update((v) => !v);
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    const raw = this.form.getRawValue();
    const payload: Habitacion = {
      numero: raw.numero,
      piso: Number(raw.piso),
      descripcion: raw.descripcion,
      idTipoHabitacion: Number(raw.idTipoHabitacion),
      precioPorNoche: Number(raw.precioPorNoche),
      estado: 'DISPONIBLE' as EstadoHabitacion,
    };
    this.habitacionService.create(payload).subscribe({
      next: () => {
        this.message.set('Habitación registrada correctamente');
        this.form.patchValue({ numero: '', descripcion: '' });
        this.showForm.set(false);
        this.load();
      },
      error: () => this.message.set('Solo administradores pueden crear habitaciones'),
    });
  }

  occupancyPercent(estado: EstadoHabitacion | undefined): number {
    if (estado === 'OCUPADA') {
      return 100;
    }
    if (estado === 'DISPONIBLE') {
      return 0;
    }
    return 50;
  }
}
