export const setWithExpiry = (key, value, expiryInMinutes) => {
  const now = new Date();
  const item = {
    value: value,
    expiry: now.getTime() + expiryInMinutes * 60 * 1000,
  };
  localStorage.setItem(key, JSON.stringify(item));
};
export const getWithExpiry = (key) => {
  const itemStr = localStorage.getItem(key);
  // If the item doesn't exist, return null
  if (!itemStr) {
    return null;
  }
  const item = JSON.parse(itemStr);
  const now = new Date();
  // Check if the item has expired
  if (now.getTime() > item.expiry) {
    // If expired, remove the item from localStorage and return null
    localStorage.removeItem(key);
    return null;
  }
  return item.value;
};
