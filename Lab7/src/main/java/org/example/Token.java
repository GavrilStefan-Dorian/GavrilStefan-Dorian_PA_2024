package org.example;

public record Token(int source, int target) {

}


//protected int source;
//protected int target;
//public Token(int source, int target) {
//    this.source = source;
//    this.target = target;
//}
//
//public int getFirst() {
//    return source;
//}
//
//public int getSecond() {
//    return target;
//}
//
//public void setFirst(int open) {
//    this.source = open;
//}
//
//public void setSecond(int closed) {
//    this.target = closed;
//}
//
//@Override
//public String toString() {
//    return "Pair{" +
//            "first=" + source +
//            ", second=" + target +
//            '}';
//}
//
//@Override
//public boolean equals(Object o) {
//    if (this == o) return true;
//    if (o == null || getClass() != o.getClass()) return false;
//    Token pair = (Token) o;
//    return Objects.equals(source, pair.source) && Objects.equals(target, pair.target);
//}
//
//
























