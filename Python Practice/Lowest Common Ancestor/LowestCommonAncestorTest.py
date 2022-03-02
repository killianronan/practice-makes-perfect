import LowestCommonAncestor
import unittest

class TestSum(unittest.TestCase):
	def test_LCA(self):
		self.assertEqual(LowestCommonAncestor.findLCA(root, 4, 5), 2, "Should be 2")
		self.assertEqual(LowestCommonAncestor.findLCA(root, 4, 6), 1, "Should be 1")
		self.assertEqual(LowestCommonAncestor.findLCA(root, 3, 4), 1, "Should be 1")
		self.assertEqual(LowestCommonAncestor.findLCA(root, 2, 4), 2, "Should be 2")

	def testInvalid_LCA(self):
		self.assertEqual(LowestCommonAncestor.findLCA(root, 4, 9), -1, "Should be -1")
		self.assertEqual(LowestCommonAncestor.findLCA(root, -1, 2), -1, "Should be -1")

	def testNullRoot_LCA(self):
		root = None;
		self.assertEqual(LowestCommonAncestor.findLCA(root, 4, 6), -1, "Should be -1 (null root)")

if __name__ == "__main__":
	root = LowestCommonAncestor.Node(1) 
	root.left = LowestCommonAncestor.Node(2) 
	root.right = LowestCommonAncestor.Node(3) 
	root.left.left = LowestCommonAncestor.Node(4) 
	root.left.right = LowestCommonAncestor.Node(5) 
	root.right.left = LowestCommonAncestor.Node(6) 
	root.right.right = LowestCommonAncestor.Node(7) 
	unittest.main()