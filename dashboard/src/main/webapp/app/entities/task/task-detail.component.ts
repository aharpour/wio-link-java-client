import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { Task } from './task.model';
import { TaskService } from './task.service';

@Component({
    selector: 'jhi-task-detail',
    templateUrl: './task-detail.component.html'
})
export class TaskDetailComponent implements OnInit, OnDestroy {

    task: Task;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private taskService: TaskService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['task']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.taskService.find(id).subscribe(task => {
            this.task = task;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
