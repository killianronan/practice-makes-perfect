{- butrfeld Andrew Butterfield -}
module Ex04 where
import Data.List

name, idno, username :: String
name      =  "Killian Ronan"  -- replace with your name
idno      =  "18328687"    -- replace with your student id
username  =  "kronan"   -- replace with your TCD username

declaration -- do not modify this
 = unlines
     [ ""
     , "@@@ This exercise is all my own work."
     , "@@@ Signed: " ++ name
     , "@@@ "++idno++" "++username
     ]


-- Datatypes -------------------------------------------------------------------

-- do not change anything in this section !


-- a binary tree datatype, honestly!
data BinTree k d
  = Branch (BinTree k d) (BinTree k d) k d
  | Leaf k d
  | Empty
  deriving (Eq, Show)


-- Part 1 : Tree Insert -------------------------------

-- Implement:
ins :: Ord k => k -> d -> BinTree k d -> BinTree k d
ins key d (Leaf key1 data1)
    | key == key1 = Leaf key d
    | key > key1  = Branch Empty (Leaf key d) key1 data1
    | key < key1  = Branch (Leaf key d) Empty key1 data1

ins key d (Branch left right key1 data1)
    | key == key1 = Branch left right key d
    | key > key1  = Branch left (ins key d right) key1 data1
    | key < key1  = Branch (ins key d left) right key1 data1

ins key d Empty = Leaf key d

ins _ _ _  = error "ins NYI"

-- Part 2 : Tree Lookup -------------------------------

-- Implement:
lkp :: (Monad m, Ord k) => BinTree k d -> k -> m d
lkp (Leaf key d) keyToFind 
    | keyToFind == key = return d 
    | otherwise        = fail "No match found" 

lkp (Branch left right key d) keyToFind 
    | keyToFind == key = return d 
    | keyToFind > key  = lkp right keyToFind
    | keyToFind < key  = lkp left keyToFind 

lkp Empty _ = fail "found Empty"

lkp _ _ = error "lkp NYI"

-- Part 3 : Tail-Recursive Statistics

{-
   It is possible to compute BOTH average and standard deviation
   in one pass along a list of data items by summing both the data
   and the square of the data.
-}
twobirdsonestone :: Double -> Double -> Int -> (Double, Double)
twobirdsonestone listsum sumofsquares len
 = (average,sqrt variance)
 where
   nd = fromInteger $ toInteger len
   average = listsum / nd
   variance = sumofsquares / nd - average * average

{-
  The following function takes a list of numbers  (Double)
  and returns a triple containing
   the length of the list (Int)
   the sum of the numbers (Double)
   the sum of the squares of the numbers (Double)

   You will need to update the definitions of init1, init2 and init3 here.
-}
getLengthAndSums :: [Double] -> (Int,Double,Double)
getLengthAndSums ds = getLASs init1 init2 init3 ds

init1 = 0
init2 = 0
init3 = 0

{-
  Implement the following tail-recursive  helper function
-}
getLASs :: Int -> Double -> Double -> [Double] -> (Int,Double,Double)
getLASs init1 init2 init3 ds
    = (listLength, numbersSum, squaresSum)
    where
        listLength = length ds + init1
        numbersSum = sum ds + init3
        squaresSum = sum (map (^2) ds) + init2

-- Final Hint: how would you use a while loop to do this?
--   (assuming that the [Double] was an array of double)
