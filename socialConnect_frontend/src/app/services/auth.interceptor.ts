import { LoginService } from './login.service';
import { Injectable } from '@angular/core';
import {
    HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HTTP_INTERCEPTORS
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptor implements HttpInterceptor {

    constructor(private loginService:LoginService){

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("interceptado");
        let authReq = req;
        const token = this.loginService.getToken();
        //console.log("El token en el interceptor es: "+token);
        if(!!token){
            req = req.clone({
                setHeaders : {
                    Authorization : 'Bearer ' + token
                }
                
            });
            
            //console.log(authReq.headers);
        }
        return next.handle(req);
    }
}

/*
export const authInterceptorProviders = [
    {
        provide : HTTP_INTERCEPTORS,
        useClass : AuthInterceptor,
        multi:true
    }
]*/