import { HttpClientModule, } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { MatButtonModule } from '@angular/material/button'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreateClientComponent } from './create-client/create-client.component';
import { AuthentificationComponent } from './authentification/authentification.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card'; 
import { MatTableModule } from '@angular/material/table'  
import { WelcomeComponent } from './welcome/welcome.component';
import {MatIconModule} from '@angular/material/icon';
import { HistoriqueComponent } from './historique/historique.component';
import { AccountBankComponent } from './account-bank/account-bank.component';
import { OperationComponent } from './operation/operation.component';
import { MatOptionModule } from '@angular/material/core';
import { MatSelectModule } from '@angular/material/select';
@NgModule({
  declarations: [
    AppComponent,
    CreateClientComponent,
    AuthentificationComponent,
    WelcomeComponent,
    HistoriqueComponent,
    HomeComponent,
    AccountBankComponent,
    OperationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCardModule,
    MatTableModule,
    MatIconModule,
    MatOptionModule,
    MatSelectModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
