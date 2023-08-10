import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseURL from './helper';
import { Observable, Subject, lastValueFrom } from 'rxjs';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public loginStatusSubjec = new Subject<boolean>();

  constructor(private http:HttpClient, private userService:UserService) {}

  public generateToken(loginData:any){
    return this.http.post(`${baseURL}/generate-token`,loginData);
  }

  public async getCurrentUser(){
    let response$ = this.http.get(`${baseURL}/actual-usuario`);
    let response = await lastValueFrom(response$);
    return response;
  }

  //Inicio de sessión y establecimiento del token
  public loginUser(token:any){
    sessionStorage.setItem('token',token);
    return true;
  }

  public isLoggedIn(){
    let tokenStr = sessionStorage.getItem('token');
    if(tokenStr == undefined || tokenStr == '' || tokenStr == null){
      return false;
    }else{
      return true;
    }
  }

  //Cierra sesión y elimina el token del localStorage
  public logout(){
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('user');
    sessionStorage.clear();
    return true;
  }

  //Obtener token
  public getToken(){
    let token =sessionStorage.getItem('token');
    return token;
  }

  public setUser(user:any){
    //console.log("[login.service] user llegando"+user)
    //console.log("[login.service] user cargando"+JSON.stringify(user))
    sessionStorage.setItem('user',JSON.stringify(user));
  }

  public getUser(){
    let userStr = sessionStorage.getItem('user');
    if(userStr != null){
      return JSON.parse(userStr);
    }else{
      //this.logout();
      return null;
    }
  }


  public isMyProfile(user:any):Boolean{
    if(user.email==this.getUser().email){
      return true;
    }else{
      return false;
    }
  }

  public updateCurrentUser(){
    this.userService.getUserDB(this.getUser().email).then((response:any) =>{
      console.log("[login.service] user: "+response.email+"|"+response.profile.follows);
      if(response!=null && response!=undefined){
        this.setUser(response);
        return response;
      }else{
        console.log("Es null o undefined");
      }
    });
    return null;
  }

  /*
  public getUserRole(){
    let user = this.getUser();
    return user.authorities[0].authority;
  }*/

}
