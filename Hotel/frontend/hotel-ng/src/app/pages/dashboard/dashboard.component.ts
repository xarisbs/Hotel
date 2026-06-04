import { Component, inject, signal, OnInit, computed } from '@angular/core';
import { DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ReportesService } from '../../services/reportes.service';
import { ReservaService } from '../../services/reserva.service';
import { DashboardEjecutivo } from '../../models/dashboard.model';
import { Reserva } from '../../models/reserva.model';
import { formatPen, formatDate, estadoBadgeClass, estadoLabel } from '../../shared/utils/format.utils';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterLink, DatePipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);
  private readonly reservaService = inject(ReservaService);

  readonly data = signal<DashboardEjecutivo | null>(null);
  readonly reservas = signal<Reserva[]>([]);
  readonly error = signal<string | null>(null);
  readonly loading = signal(true);

  readonly today = new Date();
  readonly formatPen = formatPen;
  readonly formatDate = formatDate;
  readonly estadoBadgeClass = estadoBadgeClass;
  readonly estadoLabel = estadoLabel;

  readonly disponibles = computed(() => this.data()?.habitacionesPorEstado?.['DISPONIBLE'] ?? 0);
  readonly ocupadas = computed(() => this.data()?.habitacionesPorEstado?.['OCUPADA'] ?? 0);

  readonly recentReservas = computed(() =>
    [...this.reservas()]
      .sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
      .slice(0, 5)
  );

  ngOnInit(): void {
    this.reportesService.dashboardEjecutivo().subscribe({
      next: (d) => {
        this.data.set(d);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('No se pudieron cargar las métricas. Verifica que reportes esté activo.');
        this.loading.set(false);
      },
    });

    this.reservaService.list().subscribe({
      next: (r) => this.reservas.set(r),
    });
  }

  occupancySegments(): { label: string; value: number; color: string }[] {
    const estados = this.data()?.habitacionesPorEstado ?? {};
    const colors: Record<string, string> = {
      DISPONIBLE: '#1a7f5a',
      OCUPADA: '#c0392b',
      LIMPIEZA: '#b8860b',
      MANTENIMIENTO: '#8b98a5',
      FUERA_SERVICIO: '#5c6b7a',
    };
    return Object.entries(estados).map(([label, value]) => ({
      label,
      value,
      color: colors[label] ?? '#1f4e79',
    }));
  }

  totalEstados(): number {
    return this.occupancySegments().reduce((s, e) => s + e.value, 0) || 1;
  }
}
