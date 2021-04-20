import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { AddCouponComponent } from './company/add-coupon/add-coupon.component';
import { CompanyEditComponent } from './company/company-edit/company-edit.component';
import { CompanyComponent } from './company/company.component';
import { CouponsListComponent } from './company/coupons-list/coupons-list.component';
import { CustomerEditComponent } from './customer/customer-edit/customer-edit.component';
import { CustomerComponent } from './customer/customer.component';
import { ForSaleCouponsListComponent } from './customer/for-sale-coupons-list/for-sale-coupons-list.component';
import { MyCouponsListComponent } from './customer/my-coupons-list/my-coupons-list.component';
import { ViewCustomerComponent } from './customer/view-customer/view-customer.component';
import { LoginCustomerComponent } from './customer/login-customer/login-customer.component';
import { LoginComponent } from './login/login.component';
import { LoginCompanyComponent } from './company/login-company/login-company.component';
import { ViewCompanyComponent } from './company/view-company/view-company.component';

const routes: Routes = [
  { path: "", pathMatch: "full", component: LoginComponent },

  {
    path: "customer", component: CustomerComponent, children: [
      { path: "", component: LoginCustomerComponent, pathMatch: "full" },
      {
        path: "login", component: ViewCustomerComponent, children: [
          { path: "", redirectTo: "/customer/login/my-coupons", pathMatch: "full" },
          { path: "my-coupons", component: MyCouponsListComponent },
          { path: "for-sale", component: ForSaleCouponsListComponent },
          { path: "edit", component: CustomerEditComponent }
        ]
      }
    ]
  },

  {
    path: "company", component: CompanyComponent, children: [
      { path: "", component: LoginCompanyComponent, pathMatch: "full" },
      {
        path: "login", component: ViewCompanyComponent, children: [
          { path: "", redirectTo: "/company/login/coupons", pathMatch: "full" },
          { path: "coupons", component: CouponsListComponent },
          { path: "new", component: AddCouponComponent },
          { path: "edit", component: CompanyEditComponent }
        ]
      }
    ]
  },
  { path: "admin", component: AdminComponent }
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
