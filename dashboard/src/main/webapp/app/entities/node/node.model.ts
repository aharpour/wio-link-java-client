import { Grove } from '../grove';
export class Node {
    constructor(
        public nodeSn?: string,
        public name?: string,
        public nodeKey?: string,
        public dataXServer?: string,
        public board?: string,
        public initialized?: boolean,
        public groves?: Grove,
    ) { }
}
