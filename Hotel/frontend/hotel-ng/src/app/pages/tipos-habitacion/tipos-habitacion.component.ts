import { Component, inject, signal, OnInit, computed } from '@angular/core';
import { DecimalPipe } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TipoHabitacionService } from '../../services/tipo-habitacion.service';
import { TipoHabitacion } from '../../models/tipo-habitacion.model';

@Component({
  selector: 'app-tipos-habitacion',
  standalone: true,
  imports: [ReactiveFormsModule, DecimalPipe],
  templateUrl: './tipos-habitacion.component.html',
  styleUrl: './tipos-habitacion.component.scss',
})
export class TiposHabitacionComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly tipoService = inject(TipoHabitacionService);

  readonly tipos = signal<TipoHabitacion[]>([]);
  readonly message = signal<string | null>(null);
  readonly showForm = signal(false);

  readonly form = this.fb.nonNullable.group({
    nombre: ['', Validators.required],
    descripcion: [''],
    capacidadMaxima: [2, Validators.min(1)],
    precioBase: [150, Validators.min(0.01)],
    cantidadCamas: [1, Validators.min(1)],
  });

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.tipoService.list().subscribe({
      next: (data) => this.tipos.set(data),
    });
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    this.tipoService.create(this.form.getRawValue()).subscribe({
      next: () => {
        this.message.set('Tipo de suite registrado');
        this.form.reset({ capacidadMaxima: 2, precioBase: 150, cantidadCamas: 1 });
        this.showForm.set(false);
        this.load();
      },
      error: () => this.message.set('Requiere rol de administrador'),
    });
  }
}
