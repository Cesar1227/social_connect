import { Component, Input, OnInit } from '@angular/core';
import { Iuser } from 'src/app/model/iuser';
import { Cuser } from 'src/app/model/iuser';

@Component({
  selector: 'app-publications',
  templateUrl: './publications.component.html',
  styleUrls: ['./publications.component.css']
})
export class PublicationsComponent implements OnInit {

  @Input() public createPost_isVisible:boolean = true;

  public publications : any = {

  } 

  @Input() public user = {
    password : '',
    name : '',
    lastName : '',
    email : '',
    profile : {
      nickName : '',
      cellphone : '',
      profile:''
    },
    publications: Array()
  }

  constructor() {}

  ngOnInit(): void {

  }

}
