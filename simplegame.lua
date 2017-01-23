print("Welcome to my game. Type 'inspect' to gather hints about your surroundings.")

input = ""
while input ~="leave cave" do	
  print("What do you want to do?")
  input = io.read()
  if input == "inspect" then
   print("You are in a cave (leave cave)")
  end

  if input == "leave cave" then
    print("You leave the cave")
  end
end

input = ""
while input ~="follow path" do
  print("What do you want to do?")
  input = io.read()
  if input == "inspect" then
   print("You are at the base of the hill. There is a path (follow path).")
  end

  if input == "follow path" then
    print("You follow the path")
  end
end

input = ""
have_key = false
while not (have_key == true and input == "open gate") do
  print("What do you want to do?")
  input = io.read()
  if input == "inspect" and have_key == false then
   print("There is a gate. A key is hidden in the grass. (grab key) (open gate)")
  end

  if input == "inspect" and have_key == true then
   print("There is a gate. Maybe you could try this key... (open gate)")
  end
  
  if input == "grab key" and have_key == true then
    print("You already grabbed the key. (open gate)")
  end

  if input == "grab key" and have_key == false then
    have_key = true
    print("You grabbed the key.")
  end
  if input == "open gate" then
    if have_key == true then
      print("You have opened the gate and escaped.")
    end
    if have_key == false then
      print("The gate is locked. (grab key)")
    end
  end
end

print("You win!")


