import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthentificationComponent } from './authentification/authentification.component';
import { CreateClientComponent } from './create-client/create-client.component';

const routes: Routes = [
  {
    path: 'createClient',
    component: CreateClientComponent
  },
  {
    path: 'authentification',
    component: AuthentificationComponent
  },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
