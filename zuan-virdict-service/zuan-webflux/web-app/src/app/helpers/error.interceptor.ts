import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { LoginService } from '../admin/login/login.service';
import { HttpHeaders } from '@angular/common/http/src/headers';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    constructor(private authenticationService: LoginService) { }

    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(request).pipe(catchError(err => {
            if (err.status === 401) {
                const headers: HttpHeaders = err.headers;
                if (headers.get('text') === 'BadCredentialsException') {
                    // auto logout if 401 response returned from api
                    this.authenticationService.logout();
                    location.reload(true);
                } else if (headers.get('text') === 'ExpiredJwtException') {
                    if (headers.get('authenToken')) {
                        const authenTokenJson = JSON.parse(headers.get('authenToken'));
                        const currentUser = JSON.stringify(authenTokenJson);
                        localStorage.setItem('currentUser', currentUser);
                        request = request.clone({
                            setHeaders: {
                                Authorization: `Bearer ${authenTokenJson.token}`
                            }
                        });
                    }
                }

            }
            const error = err.error.message || err.statusText;
            return throwError(error);
        }));
    }
}
