import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baseURL from './helper';
import { lastValueFrom } from 'rxjs';
import { StringMap } from '@angular/compiler/src/compiler_facade_interface';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private httpClient:HttpClient) { }

  private follow(url:string,parametros:string){
    return this.httpClient.post(url, parametros, { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }) });
  }

  public async followUser(current_user:string, nickNameToFollow:string){
    const url = `${baseURL}/profile/followUser`;
    const parametros = `current_user=${current_user}&nickNameToFollow=${nickNameToFollow}`;
    let array: any[] = [];
    let response$ = this.follow(url,parametros);
    array = await lastValueFrom(response$) as Array<any>;
    return array;
    /*
    this.httpClient.post(url, parametros, { headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' }) })
      .then(async (response:any) => {
        array = await lastValueFrom(response) as Array<any>;
        console.log("[profile.service] Seguir usuario: "+array);
        //response_ = await lastValueFrom(array);

        (error:any) => {
          console.error('Error, no se pudo seguir a este usuario:', error);
          return null;
        }
        return array;
      }); */
  }

  public async setPictureProfile(picture:File, nickName:string){
    const url = `${baseURL}/profile/setPhoto`;
    const dataFile:FormData = new FormData();
    dataFile.append("file",picture);
    dataFile.append("nickName",nickName);
    let response :any;
    //let response$ = this.httpClient.post(url, dataFile, { headers: new HttpHeaders({ 'Content-Type': 'multipart/form-data' }) });
    let response$ = this.httpClient.post(url, dataFile);
    response = await lastValueFrom(response$); 
    return response;
  }

  public async stopFollowingUser(current_user:string, nickNameToStopfollow:string){
    const url = `${baseURL}/profile/stopFollowUser`;
    const parametros = `current_user=${current_user}&nickNameToStopfollow=${nickNameToStopfollow}`;
    let array: any[] = [];
    let response$ = this.follow(url,parametros);
    array = await lastValueFrom(response$) as Array<any>;
    return array;
  }
  
}
