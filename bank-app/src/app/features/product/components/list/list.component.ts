import {Component, Input, OnDestroy, OnInit} from '@angular/core';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements  OnInit, OnDestroy {

  @Input()
  list: any[] =  [];

  columnsName: any[]

  constructor() { }

  ngOnInit(): void {
    this.columnsName = Object.getOwnPropertyNames(this.list[0])
  }

  addItem(item: any, index:number){
    const a = this.list.indexOf(item)
    const b = Object.values(this.list[a])
    console.log(b)
    return b[index]
  }

  ngOnDestroy() {
    this.list =[];
    this.columnsName =[];
  }

}
