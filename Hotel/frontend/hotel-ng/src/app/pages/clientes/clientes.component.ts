import { Component, inject, signal, OnInit, computed } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClienteService } from '../../services/cliente.service';
import { ReservaHistorial } from '../../models/cliente.model';
import { Cliente } from '../../models/cliente.model';
import { TokenStorage } from '../../core/auth/token.storage';
import {
  formatDate,
  formatPen,
  estadoBadgeClass,
  estadoLabel,
  initials,
} from '../../shared/utils/format.utils';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss',
})
export class ClientesComponent implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly clienteService = inject(ClienteService);
  private readonly tokenStorage = inject(TokenStorage);

  readonly clientes = signal<Cliente[]>([]);
  readonly historial = signal<ReservaHistorial[]>([]);
  readonly selected = signal<Cliente | null>(null);
  readonly message = signal<string | null>(null);
  readonly showForm = signal(false);
  readonly search = signal('');

  readonly formatDate = formatDate;
  readonly formatPen = formatPen;
  readonly estadoBadgeClass = estadoBadgeClass;
  readonly estadoLabel = estadoLabel;
  readonly initials = initials;

  readonly isAdmin = computed(() => this.tokenStorage.hasRole('ROLE_ADMIN'));

  readonly form = this.fb.nonNullable.group({
    nombre: ['', Validators.required],
    apellido: ['', Validators.required],
    documento: ['', Validators.required],
    email: [''],
    telefono: [''],
    nacionalidad: ['Peruana'],
  });

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.clienteService.list().subscribe({
      next: (data) => {
        this.clientes.set(data);
        if (data.length && !this.selected()) {
          this.selectGuest(data[0]);
        }
      },
      error: () => this.message.set('Error al cargar huéspedes'),
    });
  }

  onSearch(value: string): void {
    this.search.set(value);
    const q = value.trim();
    if (!q) {
      this.load();
      return;
    }
    this.clienteService.buscar(q).subscribe({
      next: (data) => this.clientes.set(data),
    });
  }

  selectGuest(c: Cliente): void {
    this.selected.set(c);
    if (!c.id) {
      this.historial.set([]);
      return;
    }
    this.clienteService.historialReservas(c.id).subscribe({
      next: (h) => this.historial.set(h),
      error: () => this.historial.set([]),
    });
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    this.clienteService.create(this.form.getRawValue()).subscribe({
      next: (c) => {
        this.message.set('Huésped registrado en Hotel Inti Suites');
        this.form.reset({ nacionalidad: 'Peruana' });
        this.showForm.set(false);
        this.load();
        this.selectGuest(c);
      },
      error: () => this.message.set('No se pudo registrar el huésped'),
    });
  }

  remove(id: number | undefined): void {
    if (!id || !this.isAdmin()) {
      return;
    }
    this.clienteService.delete(id).subscribe({
      next: () => {
        this.selected.set(null);
        this.historial.set([]);
        this.load();
      },
      error: () => this.message.set('Solo administradores pueden eliminar huéspedes'),
    });
  }
}
