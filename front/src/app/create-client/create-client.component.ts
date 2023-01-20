import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ClientFullDTO } from 'src/interface/clientFullDTO';
import { ApiService } from '../services/api.service';


@Component({
  selector: 'app-create-client',
  templateUrl: './create-client.component.html',
  styleUrls: ['./create-client.component.scss']
})
export class CreateClientComponent implements OnInit {
  public userForm: FormGroup;
  constructor(private formBuilder: FormBuilder, private apiService: ApiService, private router: Router) { 
    this.userForm = formBuilder.group({
      email: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30), Validators.pattern(this.emailRegex)]],
      nom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      prenom: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
      password: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(30)]],
    });
  }
  listErrorMail: String[]= [];
  listErrorNom: String[]= [];
  listErrorPrenom: String[]= [];
  listErrorPassword: String[]= [];
  private readonly emailRegex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  ngOnInit(): void {
  }
  onFormSubmitUser() {
    this.listErrorMail = [];
    this.listErrorNom = [];
    this.listErrorPrenom = [];
    this.listErrorPassword = [];
    if(!this.isInvalidAndDirty('email') &&  this.controleChampMail()
    && !this.isInvalidAndDirty('nom') && this.controleChamp(this.userForm.get('nom')?.value, 2)
    && !this.isInvalidAndDirty('prenom') && this.controleChamp(this.userForm.get('prenom')?.value, 2)
    && !this.isInvalidAndDirty('password') && this.controleChamp(this.userForm.get('password')?.value, 6)) {
      this.envoieDonnee();
    } else {
      this.listErrorMail = this.recupMailMessageError(); 
      this.listErrorNom = this.recupChampMessageError(this.userForm.get('nom')?.value, 2);
      this.listErrorPrenom = this.recupChampMessageError(this.userForm.get('prenom')?.value, 2);
      this.listErrorPassword = this.recupChampMessageError(this.userForm.get('password')?.value, 6);
    }
  }

  controleChampMail() : boolean{
    var mail:String = this.userForm.get('email')?.value;
    return (this.userForm.get('email')?.value != "" &&
     !this.verifyChamp('email', 'pattern')  && mail.length>=6 && mail.length<=30);      
  }

  controleChamp(champ: string, minLength:number) :  boolean{
    return (champ !=null && champ != "" && champ.length>=minLength && champ.length<=30);
  }

  recupMailMessageError() : String[]{
    var listError:String[] = [];
    if(this.userForm.get('email')?.value == "") {
      listError.push("le champs n'est pas remplit");
    } else if(this.isInvalidAndDirty('email')){
      if(this.verifyChamp('email', 'pattern')) {
        listError.push("Erreur pattern mail");
      }
      var mail:String = this.userForm.get('email')?.value;
      if(mail.length<6 || mail.length>30) {
        listError.push("la taille du champs doit être compris entre 6 et 30");
      }
    }
    return listError;
  }
  recupChampMessageError(champ: string, minLength:number) :  String[]{
    var listError:String[] = [];
    if(champ ==null || champ == "") {
      listError.push("le champs n'est pas remplit");
    } else if(champ.length<minLength || champ.length>30) {
      listError.push("la taille du champs doit être compris entre "+minLength+" et 30");
    }
    return listError;
  }

  envoieDonnee() {
    var client = new ClientFullDTO(this.userForm.get('email')?.value, 
    this.userForm.get('nom')?.value,
    this.userForm.get('prenom')?.value,
    this.userForm.get('password')?.value);
  this.apiService.createClient(client).subscribe({
      next: (response) => {
      alert("compte créer");
      this.router.navigate(['/authentification']);
    },
    error: (error) => {
      alert(error.error);
    }
  });
  }

  isInvalidAndDirty(field: string): boolean {
    const ctrl = this.userForm.get(field);
    return ctrl !== null && !ctrl.valid && ctrl.dirty;
  }

  verifyChamp(field: string, error: string): boolean {
    const ctrl = this.userForm.get(field);    
    return ctrl !== null && ctrl.dirty && ctrl.hasError(error);
  }

  resetUserForm(userForm:NgForm) {
    userForm.resetForm();
  }
  
  toHome(){
    this.router.navigate(['/']);
  }
}
