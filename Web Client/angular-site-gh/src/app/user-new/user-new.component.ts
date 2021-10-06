import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { ApiResponse } from '../api-response';

import { UtilService } from '../util.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-new',
  templateUrl: './user-new.component.html',
  styleUrls: ['./user-new.component.css']
})
export class UserNewComponent implements OnInit {
  errorResponse: ApiResponse;
  form: FormGroup;
  formErrors = {
    'r_admin':'',
    'r_name':'',
    'r_type':'',
//    'email':'',
    'r_password':'',
    'r_detail':'',
    'r_number':'',
    'r_address':'',
    'passwordConfirmation':'',
  };
  formErrorMessages = {
    'r_admin': {
      'required': 'r_admin is required!',
      'pattern': 'Should be 4-12 characters!',
    },
    'r_name': {
      'required': 'r_Name is required!',
      'pattern': 'Should be 4-12 characters!',
    },
//    'email': {
//      'pattern': 'Should be a vaild email address!',
//   },
    'r_password': {
      'required': 'r_Password is required!',
      'pattern': 'Should be minimum 8 characters of alphabet and number combination!',
    },
    'passwordConfirmation': {
      'required': 'Password Confirmation is required!',
      'match': 'Password Confirmation does not matched!',
    },
    'r_type': {
      'required': 'r_type Confirmation is required!',
      'match': 'r_type Confirmation does not matched!',
    },
    'r_number': {
      'required': 'r_Number is required!',
      'pattern': 'Should be 4-12 characters!',
    },
    'r_address': {
      'required': 'r_Address is required!',
      'pattern': 'Should be 4-12 characters!',
    },
    'r_detail': {
      'required': 'r_detail is required!',
      'pattern': 'Should be 4-12 characters!',
    },
  };
  buildForm(): void {
    this.form = this.formBuilder.group({
      r_admin:["", [Validators.required, Validators.pattern(/^.{4,12}$/)]],
      r_password:["", [Validators.required, Validators.pattern(/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$/)]],
      passwordConfirmation:["", [Validators.required]],
      r_type:["", [Validators.required, Validators.pattern(/^.{2,12}$/)]],
      r_name:["", [Validators.required, Validators.pattern(/^.{2,12}$/)]],
//      email:["", [Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]],
      r_number:["", [Validators.required, Validators.pattern(/^.{2,12}$/)]],
      r_address:["", [Validators.pattern(/^.{1,22}$/)]],
      r_detail:["", [Validators.pattern(/^.{1,22}$/)]],
    }, {
//      validator: this.customValidation,
    });

    this.form.valueChanges.subscribe(data => {
      this.utilService.updateFormErrors(this.form, this.formErrors, this.formErrorMessages);
    });
  };

  customValidation(group: FormGroup) {
    var r_password = group.get('r_password');
    var passwordConfirmation = group.get('passwordConfirmation');
    if(r_password.dirty && passwordConfirmation.dirty && r_password.value !== passwordConfirmation.value){
        passwordConfirmation.setErrors({'match': true});
    }
  }


  constructor(
    private router: Router,
    private formBuilder: FormBuilder,
    private utilService: UtilService,
    private userService: UserService,
  ) {
    this.buildForm();
  }

  ngOnInit() {
  }

  submit() {
    this.utilService.makeFormDirtyAndUpdateErrors(this.form, this.formErrors, this.formErrorMessages);
    if (this.form.valid) {
      this.userService.create(this.form.value.r_admin, this.form.value.r_password, this.form.value.r_type, this.form.value.r_name, this.form.value.r_number, this.form.value.r_address, this.form.value.r_detail)
      .then(data => {
        console.log('data is:' + data);
        this.router.navigate(['/']);
      })
      .catch(response => {
        console.log('create false');
        this.errorResponse = response;
        this.utilService.handleFormSubmitError(this.errorResponse, this.form, this.formErrors);
      });
    }
  }

}
