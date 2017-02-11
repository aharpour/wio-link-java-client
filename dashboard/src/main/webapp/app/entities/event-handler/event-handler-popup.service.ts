import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventHandler } from './event-handler.model';
import { EventHandlerService } from './event-handler.service';
@Injectable()
export class EventHandlerPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private eventHandlerService: EventHandlerService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.eventHandlerService.find(id).subscribe(eventHandler => {
                this.eventHandlerModalRef(component, eventHandler);
            });
        } else {
            return this.eventHandlerModalRef(component, new EventHandler());
        }
    }

    eventHandlerModalRef(component: Component, eventHandler: EventHandler): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.eventHandler = eventHandler;
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
