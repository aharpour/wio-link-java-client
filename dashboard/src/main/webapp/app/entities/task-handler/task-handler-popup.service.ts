import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TaskHandler } from './task-handler.model';
import { TaskHandlerService } from './task-handler.service';
@Injectable()
export class TaskHandlerPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private taskHandlerService: TaskHandlerService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.taskHandlerService.find(id).subscribe(taskHandler => {
                this.taskHandlerModalRef(component, taskHandler);
            });
        } else {
            return this.taskHandlerModalRef(component, new TaskHandler());
        }
    }

    taskHandlerModalRef(component: Component, taskHandler: TaskHandler): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.taskHandler = taskHandler;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
