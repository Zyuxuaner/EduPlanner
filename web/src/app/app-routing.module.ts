import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LayoutComponent} from "./layout/layout.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {LoginComponent} from "./login/login.component";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: '',
    component: LayoutComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule)
      },
      {
        path: 'term',
        loadChildren: () => import('./term/term.module').then(m => m.TermModule)
      },
      {
        path: 'clazz',
        loadChildren: () => import('./clazz/clazz.module').then(m => m.ClazzModule)
      },
      {
        path: 'student',
        loadChildren: () => import('./student/student.module').then(m => m.StudentModule)
      },
      {
        path:'course',
        loadChildren: () => import('./course/course.module').then(m => m.CourseModule)
      },
      {
        path:'courseTable',
        loadChildren: () => import('./course-table/course-table.module').then(m => m.CourseTableModule)
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
