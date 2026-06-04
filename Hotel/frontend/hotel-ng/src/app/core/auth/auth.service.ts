import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../config/api.config';
import { AuthLoginRequest, AuthLoginResponse } from '../../models/auth.model';
import { TokenStorage } from './token.storage';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly tokenStorage = inject(TokenStorage);

  login(request: AuthLoginRequest): Observable<AuthLoginResponse> {
    return this.http
      .post<AuthLoginResponse>(`${API_BASE_URL}/auth/login`, request)
      .pipe(
        tap((response) => {
          this.tokenStorage.save({
            accessToken: response.accessToken,
            username: response.username,
            roles: response.roles ?? [],
          });
        })
      );
  }

  logout(): void {
    this.tokenStorage.clear();
  }

  isAuthenticated(): boolean {
    return this.tokenStorage.isAuthenticated();
  }

  username(): string | null {
    return this.tokenStorage.session()?.username ?? null;
  }

  roles(): string[] {
    return this.tokenStorage.session()?.roles ?? [];
  }
}
