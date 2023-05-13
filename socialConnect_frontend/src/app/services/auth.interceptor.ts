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

        if (!req || !req.url) {
            //console.log("url"+req.url);
            return next.handle(req);
        }

        /*
        if(req.url=='http://localhost:8080/generate-token'){
            return next.handle(req);
        }*/

        const token = this.loginService.getToken();

        if(token != null){
            req = req.clone({
                setHeaders : {
                    Authorization : `Bearer ${ token }`,
                }          
            });
            
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