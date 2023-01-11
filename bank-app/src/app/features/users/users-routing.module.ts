import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UserFormComponent } from './components/user-form/user-form.component';
import {UsersListComponent} from "./components/users-list/users-list.component";
import {CreateUserComponent} from "./components/create-user/create-user.component";
import {ModifyUserComponent} from "./components/modify-user/modify-user.component";

const routes: Routes = [{
  path: '',
  component: UsersListComponent,
},
  {path: 'create',
  component: CreateUserComponent},
  {
    path: 'modify',
    component: ModifyUserComponent
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
