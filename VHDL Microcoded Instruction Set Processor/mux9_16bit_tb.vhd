
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity mux9_16bit_tb is
end mux9_16bit_tb;

architecture Behavioral of mux9_16bit_tb is
    COMPONENT mux9_16bit
   Port (  s : in  STD_LOGIC_VECTOR (3 downto 0);
           in1 : in  STD_LOGIC_VECTOR (15 downto 0);
           in2 : in  STD_LOGIC_VECTOR (15 downto 0);
           in3 : in  STD_LOGIC_VECTOR (15 downto 0);
           in4 : in  STD_LOGIC_VECTOR (15 downto 0);
           in5 : in  STD_LOGIC_VECTOR (15 downto 0);
           in6 : in  STD_LOGIC_VECTOR (15 downto 0);
           in7 : in  STD_LOGIC_VECTOR (15 downto 0);
           in8 : in  STD_LOGIC_VECTOR (15 downto 0);           
           in9 : in  STD_LOGIC_VECTOR (15 downto 0);
       
           z : out  STD_LOGIC_VECTOR (15 downto 0));
    END COMPONENT;
       --Inputs
   signal s : std_logic_vector(3 downto 0) := (others => '0');
   signal in1 : std_logic_vector(15 downto 0) := (others => '0');
   signal in2 : std_logic_vector(15 downto 0) := (others => '0');
   signal in3 : std_logic_vector(15 downto 0) := (others => '0');
   signal in4 : std_logic_vector(15 downto 0) := (others => '0');
   signal in5 : std_logic_vector(15 downto 0) := (others => '0');
   signal in6 : std_logic_vector(15 downto 0) := (others => '0');
   signal in7 : std_logic_vector(15 downto 0) := (others => '0');
   signal in8 : std_logic_vector(15 downto 0) := (others => '0');
   signal in9 : std_logic_vector(15 downto 0) := (others => '0');
 	--Output
   signal z : std_logic_vector(15 downto 0);
begin
    UUT : mux9_16bit Port Map(
        In1 => In1,
        In2 => In2,
        In3 => In3,
        In4 => In4,
        In5 => In5,
        In6 => In6,
        In7 => In7,
        In8 => In8,
        In9 => In9,
        s => s,
        z => z
   );
sim_proc: process
   begin		
        in1 <= "0000000000000001";
		in2 <= "0000000000000010";
	    in3 <= "0000000000000100";
		in4 <= "0000000000001000";
		in5 <= "0000000000010000";
		in6 <= "0000000000100000";
		in7 <= "0000000001000000";
		in8 <= "0000000010000000";
		in9 <= "0000000100000000";
      wait for 5 ns;	
        s <= "0000";
      wait for 5 ns;
        s <= "0001";
      wait for 5 ns;	
		s <= "0010";
	  wait for 5 ns;	
        s <= "0011";
      wait for 5 ns;	
		s <= "0100";
	  wait for 5 ns;	
        s <= "0101";
      wait for 5 ns;	
		s <= "0110";
	  wait for 5 ns;	
        s <= "0111";
      wait for 5 ns;	
		s <= "1000";
   end process;
end Behavioral;