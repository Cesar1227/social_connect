import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import baseURL from './helper';
import Swal from 'sweetalert2';
import { Cuser, Iuser } from '../model/iuser';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //private user:Iuser;

  constructor(private httpClient:HttpClient) { }

  public addUser(user:any){
    return this.httpClient.post(`${baseURL}/users/createUser`,user);
  }

  public getUser(email:string){
    return this.httpClient.get(`${baseURL}/users/${email}`);
  }

  public addPublication(post:any){
    console.log("enviando petición desde userService");
    return this.httpClient.post(`${baseURL}/publications`, post);
  }

  public getUserDB(email:string):any{
    this.getUser(email).subscribe((user:any) =>{
      //this.loginService.loginStatusSubjec.next(true);
      if(user!=null){
        //window.location.href = '/dashboard';
        return user;
      }else{
        return null;
      }
    })
  }

  public getUserDBByNickName(nickname:string):any{
    this.getUser(nickname).subscribe((user:any) =>{
      //this.loginService.loginStatusSubjec.next(true);
      return user;
    })
    return null;
  }


}
