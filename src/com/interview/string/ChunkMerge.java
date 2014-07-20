package com.interview.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.interview.graph.MinHeap;
import com.interview.graph.MinHeap.Node;

public class ChunkMerge {
	
	public List<Integer> mergeChunksOfDifferentSize(List<List<Integer>> chunks){
		List<Integer> result = new ArrayList<Integer>();

		int sum[] = new int[chunks.size()+1];
		sum[0] = 0;
		for(int i =1; i < sum.length;i++){
			sum[i] = sum[i-1] + chunks.get(i-1).size();
		}
		
		for(List<Integer> chunk : chunks){
			for(Integer i : chunk){
				result.add(i);
			}
		}
		mergeSort(result,0,chunks.size()-1,sum);
		return result;
	}

	private void mergeSort(List<Integer> result,int start,int end,int sum[]){
		if(start >= end){
			return;
		}
		int mid = (start + end)/2;
		mergeSort(result,start,mid,sum);
		mergeSort(result,mid+1,end,sum);
		sortedMerge(result,start,end,sum);
	}

	private void sortedMerge(List<Integer> result,int start,int end,int sum[]){
		
		/**
		 * If chunks are of equal size then
		 * i = size*start to (mid+1)*size -1
		 * j = (mid+1)*size to size*(end+1)
		 */
		
		int mid = (start + end)/2; 
		int i = sum[start];
		int j = sum[mid+1];
		List<Integer> temp = new ArrayList<Integer>();
		while(i < sum[mid+1] && j < sum[end+1]){
			if(result.get(i) < result.get(j)){
				temp.add(result.get(i));
				i++;
			}else{
				temp.add(result.get(j));
				j++;
			}
		}
		while(i < sum[mid+1]){
			temp.add(result.get(i));
			i++;
		}
		while(j < sum[end+1]){
			temp.add(result.get(j));
			j++;
		}
		int index = sum[start];
		for(int k : temp){
			result.set(index, k);
			index++;
		}
	}
	public List<Integer> mergeChunksUsingHeap(List<List<Integer>> chunks){
		List<Integer> result = new ArrayList<Integer>();
		
		int index[] = new int[chunks.size()];
		MinHeap<Integer> heap = new MinHeap<Integer>();
		for(int i=0; i < index.length ; i++){
			heap.add(i, chunks.get(i).get(0));
		}
		while(!heap.isEmpty()){
			Node<Integer> node = heap.extractMinNode();
			result.add(node.weight);
			index[node.data]++;
			if(index[node.data] < chunks.get(node.data).size()){
				heap.add(node.data, chunks.get(node.data).get(index[node.data]));
			}
		}
		return result;
	}
	
	public static void main(String args[]){
		Integer arr1[] = {1,5,6,9,21};
		Integer arr2[] = {4,6,11,14};
		Integer arr3[] = {-1,0,7};
		Integer arr4[] = {-4,-2,11,14,18};
		Integer arr5[] = {2,6};
 		Integer arr6[] = {-5,-2,1,5,7,11,14};
 		Integer arr7[] = {-6,-1,0,15,17,22,24};
 		
		List<Integer> list1 = Arrays.asList(arr1);
		List<Integer> list2 = Arrays.asList(arr2);
		List<Integer> list3 = Arrays.asList(arr3);
		List<Integer> list4 = Arrays.asList(arr4);
		List<Integer> list5 = Arrays.asList(arr5);
		List<Integer> list6 = Arrays.asList(arr6);
		List<Integer> list7 = Arrays.asList(arr7);
		
		
		List<List<Integer>> chunks = new ArrayList<List<Integer>>();
		chunks.add(list1);
		chunks.add(list2);
		chunks.add(list3);
		chunks.add(list4);
		chunks.add(list5);
		chunks.add(list6);
		chunks.add(list7);
		
		ChunkMerge cm = new ChunkMerge();
		List<Integer> result = cm.mergeChunksOfDifferentSize(chunks);
		System.out.println(result.size());
		for(Integer r : result){
			System.out.print(r + " ");
		}
	}
}