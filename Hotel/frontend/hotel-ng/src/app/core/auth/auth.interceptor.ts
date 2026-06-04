import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenStorage } from './token.storage';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const token = inject(TokenStorage).token;
  if (!token || req.url.includes('/auth/login')) {
    return next(req);
  }
  return next(
    req.clone({
      setHeaders: { Authorization: `Bearer ${token}` },
    })
  );
};
