import { Component, inject, signal, OnInit } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { FacturacionService } from '../../services/facturacion.service';
import { ReservaService } from '../../services/reserva.service';
import { Pago, MetodoPago } from '../../models/pago.model';
import { Reserva } from '../../models/reserva.model';
import { formatPen, formatDate, estadoBadgeClass, estadoLabel } from '../../shared/utils/format.utils';

@Component({
  selector: 'app-facturacion',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './facturacion.component.html',
  styleUrl: './facturacion.component.scss',
})
export class FacturacionComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly facturacionService = inject(FacturacionService);
  private readonly reservaService = inject(ReservaService);

  readonly pagos = signal<Pago[]>([]);
  readonly reservas = signal<Reserva[]>([]);
  readonly message = signal<string | null>(null);
  readonly showForm = signal(false);

  readonly formatPen = formatPen;
  readonly formatDate = formatDate;
  readonly estadoBadgeClass = estadoBadgeClass;
  readonly estadoLabel = estadoLabel;

  readonly metodos: MetodoPago[] = ['EFECTIVO', 'TARJETA', 'YAPE', 'PLIN', 'TRANSFERENCIA'];

  readonly form = this.fb.nonNullable.group({
    reservaId: [0, Validators.min(1)],
    monto: [0, Validators.min(0.01)],
    metodoPago: ['EFECTIVO' as MetodoPago, Validators.required],
  });

  ngOnInit(): void {
    this.load();
    this.reservaService.list().subscribe({
      next: (r) => {
        this.reservas.set(r);
        if (r.length && this.form.controls.reservaId.value === 0) {
          this.form.patchValue({ reservaId: r[0].id ?? 0, monto: r[0].total ?? 0 });
        }
      },
    });
  }

  load(): void {
    this.facturacionService.list().subscribe({
      next: (p) => this.pagos.set(p),
    });
  }

  onReservaChange(id: number): void {
    const reserva = this.reservas().find((r) => r.id === id);
    if (reserva?.total) {
      this.form.patchValue({ monto: reserva.total });
    }
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    const raw = this.form.getRawValue();
    this.facturacionService
      .create({
        reservaId: Number(raw.reservaId),
        monto: Number(raw.monto),
        metodoPago: raw.metodoPago,
        estado: 'PAGADO',
      })
      .subscribe({
        next: () => {
          this.message.set('Pago registrado correctamente');
          this.showForm.set(false);
          this.load();
        },
        error: () => this.message.set('No se pudo registrar el pago'),
      });
  }
}
