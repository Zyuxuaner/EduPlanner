import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";
import {AuthGuard} from "./core/guard/auth.guard";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    // 空路径重定向到登录页面
    redirectTo: '/login',
    pathMatch: 'full'
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard]
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule),
        canActivate: [AuthGuard]
      },
      {
        path: 'term',
        loadChildren: () => import('./term/term.module').then(m => m.TermModule),
        canActivate: [AuthGuard]
      },
      {
        path: 'clazz',
        loadChildren: () => import('./clazz/clazz.module').then(m => m.ClazzModule),
        canActivate: [AuthGuard]
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule),
        canActivate: [AuthGuard]
      },
      {
        path:'courseTable',
        loadChildren: () => import('./course-table/course-table.module').then(m => m.CourseTableModule),
        canActivate: [AuthGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
