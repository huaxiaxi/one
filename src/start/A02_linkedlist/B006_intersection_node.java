package start.A02_linkedlist;

import java.util.Scanner;

import static start.A02_linkedlist.ListNode.arrayTransLinkedList;
import static start.A02_linkedlist.ListNode.printListNode;

public class B006_intersection_node {
    public static void main(String[] args) {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println();
            System.out.println("请输入链表：");
            String scIn = sc.nextLine();
            String secIn = sc.nextLine();
//            Integer target = sc.nextInt();
            ListNode firstNode = getListNode(scIn);
            ListNode secondNode = getListNode(secIn);

            ListNode result = getIntersectionNode(firstNode, secondNode);
            if (result == null){
                System.out.println("null");
            }else
            System.out.println(result.val);
        }
    }

    public static ListNode getListNode(String scIn) {
        if (scIn.length() == 0 || scIn.isBlank()) {
            System.out.println(-1);
            return null;
        }
        String[] arrStr = scIn.split(" ");
        int[] arr = new int[arrStr.length];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(arrStr[i]);
        }
        ListNode head = arrayTransLinkedList(arr);
        return head;
    }

    // 双指针法
    private static ListNode getIntersectionNode(ListNode first, ListNode second) {
        if (first == null || second == null){
            return null;
        }
        ListNode o = first;
        ListNode t = second;
        ListNode large, small;
        while (o != null && t != null) {
            o = o.next;
            t = t.next;
        }
        if (o != null){
            large = first;
            small = second;
            while (o != null) {
                large = large.next;
                o = o.next;
            }
        }else {
            large = second;
            small = first;
            while (t != null) {
                large = large.next;
                t = t.next;
            }
        }
        while (small != null){
            if (small.equals(large)){
                return small;
            }
            large = large.next;
            small = small.next;

        }

        return null;
    }

}
