export const enum Language {
    'GROOVYSCRIPT',
    'JAVASCRIPT',
    'JAVA'
};
export class EventHandler {
    constructor(
        public id?: number,
        public name?: string,
        public language?: Language,
        public code?: string,
    ) { }
}
