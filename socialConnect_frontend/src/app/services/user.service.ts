import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import baseURL from './helper';
import Swal from 'sweetalert2';
import { Cuser, Iuser } from '../model/iuser';
import { lastValueFrom } from 'rxjs';
import { ProfileService } from './profile.service';
import { resolve } from 'dns';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  //private user:Iuser;

  constructor(private httpClient: HttpClient, private profileService:ProfileService) {
  }

  public addUser(user: any) {
    return this.httpClient.post(`${baseURL}/users/createUser`, user);
  }

  public updateUser(user: any) {
    return this.httpClient.post(`${baseURL}/users/updateUser`, user);
  }

  public async addUserWithPicture(user:any, profilePicture:File, option="add"){
    let data2$;
    if(option=="add"){
      //console.log("va por add");
      data2$ = this.addUser(user);
      
    }else{
      //console.log("va por update");
      data2$ = this.updateUser(user);
    }
    let data2 = await lastValueFrom(data2$);
    let profile = await this.profileService.setPictureProfile(profilePicture,user.profile.nickName);
    let response:Map<any,any> = new Map<any,any>();
    response.set("user",data2);
    response.set("profile",profile);
    return response;
  }

  private getUser(email:string){
    return this.httpClient.get(`${baseURL}/users/${email}`);
  }

  private getUser2(email:string){
    return this.httpClient.get(`${baseURL}/users/${email}`).subscribe(
      (user:any) => {
        return user;
      }
    );
  }

  public addPublication(post: any) {
    return this.httpClient.post(`${baseURL}/publications`, post);
  }

  public async getUserDB(email: string): Promise<any> {
    //return this.getUser(email);
    let data2$ = this.getUser(email);
    let data2 = await lastValueFrom(data2$);
    //console.log("[user.service] Usuario devuelvo lastValue: "+data2.valueOf());
    return data2;

   /* let data;
    this.getUser(email).subscribe(async (user: any) => {
      //this.loginService.loginStatusSubjec.next(true);
      data = await lastValueFrom(user);
    });
    return data;*/
  }

  public getUserDBByNickName(nickname: string): any {
    this.getUser(nickname).subscribe((user: any) => {
      //this.loginService.loginStatusSubjec.next(true);
      return user;
    })
    return null;
  }


}
