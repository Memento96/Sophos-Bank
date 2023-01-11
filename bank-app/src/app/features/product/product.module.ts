import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductRoutingModule } from './product-routing.module';
import { NewProductComponent } from './components/new-product/new-product.component';
import { ModifyProductComponent } from './components/modify-product/modify-product.component';
import { ListTransactionsByProductsComponent as ListProductsComponent } from './components/list-products/list-products.component';
import { ListProductClientComponent } from './components/list-product-client/list-product-client.component';
import { ProductFormComponent } from './components/product-form/product-form.component';
import { ListComponent } from './components/list/list.component';
import { BrowserModule } from '@angular/platform-browser';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
    declarations: [
        NewProductComponent,
        ModifyProductComponent,
        ListProductsComponent,
        ListProductClientComponent,
        ProductFormComponent,
        ListComponent
    ],
    imports: [
        CommonModule,
        ProductRoutingModule,
        ReactiveFormsModule,
    ],
    exports: [
        ListComponent
    ],
    providers: []
})
export class ProductModule { }
