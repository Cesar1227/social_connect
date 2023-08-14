import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {

  @Output() paginaEmitter: EventEmitter<string> =  new EventEmitter();

  constructor() { }

  value = '';

  ngOnInit(): void {
  }

  buscar(){
    this.paginaEmitter.emit(this.value);
  }

}
