import { PollOption } from "./poll.option.interface";

export interface Poll {
    id: Number | null,
    title: String,
    options: PollOption[];
}