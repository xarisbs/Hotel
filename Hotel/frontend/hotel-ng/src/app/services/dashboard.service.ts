import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/config/api.config';
import { DashboardPrincipal } from '../models/dashboard.model';

@Injectable({ providedIn: 'root' })
export class DashboardService {
  private readonly http = inject(HttpClient);

  principal(): Observable<DashboardPrincipal> {
    return this.http.get<DashboardPrincipal>(
      `${API_BASE_URL}/api/v1/reportes/dashboard`
    );
  }
}
