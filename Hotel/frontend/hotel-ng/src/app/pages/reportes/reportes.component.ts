import { Component, inject, signal, OnInit } from '@angular/core';
import { ReportesService } from '../../services/reportes.service';
import { ReportesEjecutivos } from '../../models/dashboard.model';
import { formatPen } from '../../shared/utils/format.utils';

@Component({
  selector: 'app-reportes',
  standalone: true,
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.scss',
})
export class ReportesComponent implements OnInit {
  private readonly reportesService = inject(ReportesService);

  readonly data = signal<ReportesEjecutivos | null>(null);
  readonly error = signal<string | null>(null);
  readonly hoveredBar = signal<number | null>(null);

  readonly formatPen = formatPen;

  readonly monthlyIncome = () => {
    const d = this.data()?.ingresosPorMes ?? {};
    return Object.entries(d).map(([label, value]) => ({
      label,
      value: Number(value),
      display: formatPen(Number(value)),
    }));
  };

  readonly monthlyReservas = () => {
    const d = this.data()?.reservasPorMes ?? {};
    return Object.entries(d).map(([label, value]) => ({ label, value: Number(value) }));
  };

  ngOnInit(): void {
    this.reportesService.ejecutivos().subscribe({
      next: (d) => this.data.set(d),
      error: () => this.error.set('No se pudieron cargar los reportes'),
    });
  }

  maxIncome(): number {
    return Math.max(...this.monthlyIncome().map((b) => b.value), 1);
  }

  maxReservas(): number {
    return Math.max(...this.monthlyReservas().map((b) => b.value), 1);
  }

  barHeight(value: number, max: number): number {
    return Math.max((value / max) * 100, value > 0 ? 8 : 0);
  }
}
