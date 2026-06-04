import { Injectable, signal } from '@angular/core';
import { AuthSession } from '../../models/auth.model';

const STORAGE_KEY = 'hotel_auth_session';

@Injectable({ providedIn: 'root' })
export class TokenStorage {
  readonly session = signal<AuthSession | null>(this.read());

  save(session: AuthSession): void {
    sessionStorage.setItem(STORAGE_KEY, JSON.stringify(session));
    this.session.set(session);
  }

  clear(): void {
    sessionStorage.removeItem(STORAGE_KEY);
    this.session.set(null);
  }

  get token(): string | null {
    return this.session()?.accessToken ?? null;
  }

  isAuthenticated(): boolean {
    return !!this.token;
  }

  hasRole(role: string): boolean {
    return this.session()?.roles.includes(role) ?? false;
  }

  private read(): AuthSession | null {
    const raw = sessionStorage.getItem(STORAGE_KEY);
    if (!raw) {
      return null;
    }
    try {
      return JSON.parse(raw) as AuthSession;
    } catch {
      return null;
    }
  }
}
