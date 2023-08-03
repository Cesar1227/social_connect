import { Component, Input, OnInit } from '@angular/core';
import { Iuser } from 'src/app/model/iuser';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  @Input() user_found:boolean = false;

  constructor() { }

  public createPost_isVisible:boolean = false;

  public user_to_visit:Iuser | undefined;

  public user_nickName:string = "";

  public discover_people:boolean = false;

  ngOnInit(): void {
  }

  buscarUser(userToSearch:string){
    if(userToSearch.trim()=="" || userToSearch==null){
      //Refresar publicaciones aleatorias
      this.user_nickName="";
      this.discover_people = false;
    }else{
      this.user_nickName=userToSearch;
      this.discover_people = true;
      this.createPost_isVisible = false;
    }
  }
}
