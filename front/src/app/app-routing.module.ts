import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountBankComponent } from './account-bank/account-bank.component';
import { AuthentificationComponent } from './authentification/authentification.component';
import { CreateClientComponent } from './create-client/create-client.component';
import { HistoriqueComponent } from './historique/historique.component';
import { HomeComponent } from './home/home.component';
import { OperationComponent } from './operation/operation.component';
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
  },
  {
    path: 'accountBank',
    component: AccountBankComponent
  },
  {
    path: 'operation',
    component: OperationComponent
  },];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
