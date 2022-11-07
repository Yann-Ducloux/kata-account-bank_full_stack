import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { InfoUtilisateur } from 'src/interface/infoUtilisateur';
import { ApiService } from '../services/api.service';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.scss']
})
export class AuthentificationComponent implements OnInit {

  public authentificationForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private storageService:StorageService, private apiService: ApiService,  private router: Router) { 
    this.authentificationForm = formBuilder.group({
      email: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30), Validators.pattern(this.emailRegex)]],
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    });
  }
  listErrorMail: String[]= [];
  listErrorPassword: String[]= [];
  private readonly emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  ngOnInit(): void {
  }
  onFormSubmitAuthentification() {
    this.listErrorMail = [];
    this.listErrorPassword = [];
    if(!this.isInvalidAndDirty('email') && this.authentificationForm.get('email')?.value!=""
    && !this.isInvalidAndDirty('password') && this.authentificationForm.get('password')?.value!="") {
      this.envoieDonnee();
    } else {
      this.listErrorMail = this.controleChampMail(); 
      this.listErrorPassword = this.controleChamp(this.authentificationForm.get('password')?.value);
    }
  }
  controleChampMail() : String[]{
    var listError:String[] = [];
    if(this.authentificationForm.get('email')?.value == "") {
      listError.push("le champs n'est pas remplit");
    } else if(this.isInvalidAndDirty('email')){
      if(this.verifyChamp('email', 'pattern')) {
        listError.push("Erreur pattern mail");
      }
      var mail:String = this.authentificationForm.get('email')?.value;
      if(mail.length<6 || mail.length>30) {
        listError.push("la taille du champs doit être compris entre 6 et 30");
      }
    }
    return listError;
  }
  controleChamp(champ: string) :  String[]{
    var listError:String[] = [];
    if(champ ==null || champ == "") {
      listError.push("le champs n'est pas remplit");
    } else if(champ.length<6 || champ.length>30) {
      listError.push("la taille du champs doit être compris entre 6 et 30");
    }
    return listError;
  }

  envoieDonnee() {
    var infoUtilisateur = new InfoUtilisateur(this.authentificationForm.get('email')?.value, 
    this.authentificationForm.get('password')?.value);
  console.log(infoUtilisateur);
  this.apiService.authentification(infoUtilisateur).subscribe({
      next: (response) => {
        this.storageService.saveData('token', response.token);
        this.redirectionWelcome();
    },
    error: (error) => {
      alert(error.error);
    }
  });
  }

  
  redirectionWelcome(){
    this.router.navigate(['/welcome']);
  }
  isInvalidAndDirty(field: string): boolean {
    const ctrl = this.authentificationForm.get(field);
    return ctrl !== null && !ctrl.valid && ctrl.dirty;
  }
  verifyChamp(field: string, error: string): boolean {
    const ctrl = this.authentificationForm.get(field);    
    return ctrl !== null && ctrl.dirty && ctrl.hasError(error);
  }
  resetUserForm(authentificationForm:NgForm) {
    authentificationForm.resetForm();
  }

}
