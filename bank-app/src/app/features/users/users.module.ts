import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UserFormComponent } from './components/user-form/user-form.component';
import { UsersListComponent } from './components/users-list/users-list.component';
import { ProductModule } from "../product/product.module";
import { CreateUserComponent } from './components/create-user/create-user.component';
import { ModifyUserComponent } from './components/modify-user/modify-user.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UserFormComponent,
    UsersListComponent,
    CreateUserComponent,
    ModifyUserComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    ProductModule,
    ReactiveFormsModule
  ]
})
export class UsersModule { }
