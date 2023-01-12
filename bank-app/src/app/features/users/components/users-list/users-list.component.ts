import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { ProductsService } from 'src/app/core/services/products.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit {

  list$: Observable<any>

  constructor(private productService: ProductsService) { }

  ngOnInit() {

    this.list$ = this.productService.getAllClients()

    // this.productService.getAllClients().subscribe((r) => {this.list$ = r})

  }

}
