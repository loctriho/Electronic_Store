import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'roundDown'
})
export class RoundDownPipe implements PipeTransform {
  transform(value: number): number {
    return parseFloat((Math.floor(value * 100) / 100).toFixed(2));
  }
}
