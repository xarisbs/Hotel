import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { AuthService } from '../core/auth/auth.service';
import { TokenStorage } from '../core/auth/token.storage';

interface NavItem {
  path: string;
  label: string;
  icon: string;
  adminOnly?: boolean;
}

@Component({
  selector: 'app-shell',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './shell.component.html',
  styleUrl: './shell.component.scss',
})
export class ShellComponent {
  private readonly auth = inject(AuthService);
  private readonly tokenStorage = inject(TokenStorage);
  private readonly router = inject(Router);

  readonly sidebarOpen = signal(false);

  readonly navItems: NavItem[] = [
    {
      path: '/dashboard',
      label: 'Dashboard',
      icon: 'dashboard',
    },
    {
      path: '/habitaciones',
      label: 'Habitaciones',
      icon: 'bed',
    },
    {
      path: '/reservas',
      label: 'Reservas',
      icon: 'calendar',
    },
    {
      path: '/facturacion',
      label: 'Facturación',
      icon: 'billing',
    },
    {
      path: '/clientes',
      label: 'Huéspedes',
      icon: 'guests',
    },
    {
      path: '/reportes',
      label: 'Reportes',
      icon: 'chart',
    },
    {
      path: '/tipos-habitacion',
      label: 'Tipos de suite',
      icon: 'settings',
      adminOnly: true,
    },
  ];

  username(): string | null {
    return this.auth.username();
  }

  roleLabel(): string {
    const roles = this.auth.roles();
    if (roles.some((r) => r.includes('ADMIN'))) {
      return 'Administrador';
    }
    if (roles.some((r) => r.includes('RECEPCION'))) {
      return 'Recepción';
    }
    return roles[0]?.replace('ROLE_', '') ?? 'Usuario';
  }

  isAdmin(): boolean {
    return this.tokenStorage.hasRole('ROLE_ADMIN');
  }

  visibleNav(): NavItem[] {
    return this.navItems.filter((item) => !item.adminOnly || this.isAdmin());
  }

  toggleSidebar(): void {
    this.sidebarOpen.update((v) => !v);
  }

  closeSidebar(): void {
    this.sidebarOpen.set(false);
  }

  logout(): void {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
