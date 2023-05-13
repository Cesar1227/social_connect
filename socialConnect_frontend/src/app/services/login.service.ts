import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseURL from './helper';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public loginStatusSubjec = new Subject<boolean>();

  constructor(private http:HttpClient) {}

  public generateToken(loginData:any){
    return this.http.post(`${baseURL}/generate-token`,loginData);
  }

  public getCurrentUser(){
    return this.http.get(`${baseURL}/actual-usuario`);
  }

  //Inicio de sessión y establecimiento del token
  public loginUser(token:any){
    localStorage.setItem('token',token);
    return true;
  }

  public isLoggedIn(){
    let tokenStr = localStorage.getItem('token');
    if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
      return false;
    }else{
      return true;
    }
  }

  //Cierra sesión y elimina el token del localStorage

  public logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return true;
  }

  //Obtener token
  public getToken(){
    let token =localStorage.getItem('token');
    return token;
  }

  public setUser(user:any){
    localStorage.setItem('user',JSON.stringify(user));
  }

  public getUser(){
    let userStr = localStorage.getItem('user');
    if(userStr != null){
      return JSON.parse(userStr);
    }else{
      //this.logout();
      return null;
    }
  }

  /*
  public getUserRole(){
    let user = this.getUser();
    return user.authorities[0].authority;
  }*/

}
