import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './core/auth/auth.guard';
import { ShellComponent } from './layout/shell.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { HabitacionesComponent } from './pages/habitaciones/habitaciones.component';
import { TiposHabitacionComponent } from './pages/tipos-habitacion/tipos-habitacion.component';
import { ReservasComponent } from './pages/reservas/reservas.component';
import { ReportesComponent } from './pages/reportes/reportes.component';
import { FacturacionComponent } from './pages/facturacion/facturacion.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [guestGuard] },
  {
    path: '',
    component: ShellComponent,
    canActivate: [authGuard],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'habitaciones', component: HabitacionesComponent },
      { path: 'reservas', component: ReservasComponent },
      { path: 'facturacion', component: FacturacionComponent },
      { path: 'clientes', component: ClientesComponent },
      { path: 'reportes', component: ReportesComponent },
      { path: 'tipos-habitacion', component: TiposHabitacionComponent },
    ],
  },
  { path: '**', redirectTo: 'dashboard' },
];
