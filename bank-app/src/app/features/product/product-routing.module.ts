import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListTransactionsByProductsComponent as ListProductsComponent } from './components/list-products/list-products.component';
import { ModifyProductComponent } from './components/modify-product/modify-product.component';
import { NewProductComponent } from './components/new-product/new-product.component';
import { ListProductClientComponent } from './components/list-product-client/list-product-client.component';

const routes: Routes = [
  {
    path: '',
    component: ListProductsComponent
  },
  {
    path: 'modify',
    component: ModifyProductComponent
  },
  {
    path: 'create',
    component: NewProductComponent
  },
  {
    path: 'list-product-by-client',
    component: ListProductClientComponent
  },
  {
    path: 'list-transaction-by-product',
    component: ListProductsComponent
  },

  ];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductRoutingModule { }
