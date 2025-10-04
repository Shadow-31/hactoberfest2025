def count_file_stats(filename):
    try:
        with open(filename, "r", encoding="utf-8") as file:
            text = file.read()

        # Calculate stats
        lines = text.splitlines()
        words = text.split()
        characters = len(text)

        print("📊 File Analysis Result:")
        print(f"➡️ Total Lines: {len(lines)}")
        print(f"➡️ Total Words: {len(words)}")
        print(f"➡️ Total Characters: {characters}")

    except FileNotFoundError:
        print("❌ File not found! Please check the filename or path.")


if __name__ == "__main__":
    filename = input("Enter the filename (with path if needed): ")
    count_file_stats(filename)
