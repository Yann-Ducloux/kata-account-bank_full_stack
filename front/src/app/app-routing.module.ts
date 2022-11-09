import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthentificationComponent } from './authentification/authentification.component';
import { CreateClientComponent } from './create-client/create-client.component';
import { HistoriqueComponent } from './historique/historique.component';
import { HomeComponent } from './home/home.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'createClient',
    component: CreateClientComponent
  },
  {
    path: 'authentification',
    component: AuthentificationComponent
  },
  {
    path: 'welcome',
    component: WelcomeComponent
  },
  {
    path: 'historique',
    component: HistoriqueComponent
  },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
