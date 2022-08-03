package start.A02_linkedlist;

import java.util.Objects;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(){
    }

    public ListNode(int val){
        this.val = val;
    }
    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
    public static void printListNode(ListNode head) {
        if (head == null){
            System.out.println(-1);
            return;
        }
        while (head != null){
            System.out.print(head.val + " ");
            head = head.next;
        }
    }
    public static void printListNode(ListNode head, int printCount) {
        if (head == null){
            System.out.println(-1);
            return;
        }
        int start = 0;
        while (head != null && start < printCount){
            System.out.print(head.val + " ");
            head = head.next;
            start++;
        }
    }

    public static ListNode arrayTransLinkedList(int[] arr) {
        ListNode head = new ListNode(arr[0]);
        ListNode next = head;
        for (int i = 1; i < arr.length; i++) {
            ListNode node = new ListNode(arr[i]);
            next.next = node;
            next = node;
        }
        return head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return val == listNode.val &&
                Objects.equals(next, listNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, next);
    }
}